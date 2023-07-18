const baseURL = 'http://localhost:8090';
let results = [];

async function findGameByResult(result) {
    const gameData = await fetchGameData();
    console.log(gameData);
    if (gameData) {
        for (const game of gameData) {
            let gameResult = game.result;
            if (gameResult && gameResult.id === result.id) {
                return game;
            }
        }
    }
    return null;
}

async function createResultTable() {
    const tbody = document.querySelector('.result tbody');
    tbody.innerHTML = ''; // Clear the table body before populating it again

    for (const result of results) {
        const row = document.createElement('tr');

        const game = await findGameByResult(result);
        console.log(game);

        const teamOneCell = document.createElement('td');
        let teamOne = game.teamOne;
        teamOneCell.textContent = teamOne.name;
        row.appendChild(teamOneCell);

        const teamTwoCell = document.createElement('td');
        let teamTwo = game.teamTwo;
        teamTwoCell.textContent = teamTwo.name;
        row.appendChild(teamTwoCell);

        const resultCell = document.createElement('td');
        resultCell.textContent = result.goalsTeamOne + '-' + result.goalsTeamTwo;
        row.appendChild(resultCell);

        tbody.appendChild(row);
    }
}

async function fetchResultData() {
    const response = await fetch(baseURL + '/results', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    results = await response.json();
    if (response.ok) {
        createResultTable();
    } else {
        console.log('Error fetching game data.');
    }
}

async function getGameById(gameId) {
    const responseJson = await fetch(
        baseURL + `/games/` + gameId,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });
    if (responseJson.ok) {
        return responseJson.json();
    } else {
        return null;
    }
}

function generateRandomScore() {
    const maxGoals = 5;
    const teamGoals = Math.floor(Math.random() * (maxGoals + 1));
    return teamGoals;
}

async function fetchResults() {
    const response = await fetch(baseURL + '/results', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (response.ok) {
        return response.json()
    } else {
        return null;
    }
}

async function updateTeams(game, theResult) {
    let teamOne = game.teamOne;
    let teamTwo = game.teamTwo;
    if (theResult.goalsTeamOne > theResult.goalsTeamTwo) {
        teamOne.victories += 1;
        teamOne.goalsScored += theResult.goalsTeamOne;
        teamOne.goalsReceived += theResult.goalsTeamTwo;
        teamTwo.defeats += 1;
        teamTwo.goalsScored += theResult.goalsTeamTwo;
        teamTwo.goalsReceived += theResult.goalsTeamOne;
    }
    if (theResult.goalsTeamOne < theResult.goalsTeamTwo) {
        teamTwo.victories += 1;
        teamTwo.goalsScored += theResult.goalsTeamTwo;
        teamTwo.goalsReceived += theResult.goalsTeamOne;
        teamOne.defeats += 1;
        teamOne.goalsScored += theResult.goalsTeamOne;
        teamOne.goalsReceived += theResult.goalsTeamTwo;
    }
    if (theResult.goalsTeamOne === theResult.goalsTeamTwo) {
        teamOne.draws += 1;
        teamOne.goalsScored += theResult.goalsTeamOne;
        teamOne.goalsReceived += theResult.goalsTeamTwo;
        teamTwo.draws += 1;
        teamTwo.goalsScored += theResult.goalsTeamTwo;
        teamTwo.goalsReceived += theResult.goalsTeamOne;
    }

    const responseOne = await fetch(baseURL + '/teams/' + teamOne.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(teamOne)
    });
    const responseTwo = await fetch(baseURL + '/teams/' + teamTwo.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(teamTwo)
    });

    if (responseOne.ok && responseTwo.ok){
        fetchResultData();
    }
    else{
        console.log("probleme");
    }
}

async function updateGameResult(game, goalsTeamOne, goalsTeamTwo) {
    resultData = await fetchResults();
    let theResult;
    if (resultData){
        resultData.forEach(result =>{
            if (result.goalsTeamOne === goalsTeamOne && result.goalsTeamTwo === goalsTeamTwo){
                game.result = result;
                theResult = result;
            }
        })
    }
    const response = await fetch(
        baseURL + `/games/` + game.id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(game)
        }
    );
    if (response.ok){
        updateTeams(game, theResult);
    }
    else
        console.log("Probleme");
}

async function createResult() {
    const game = await getGameById($('#gameAdd').val());
    const goalsTeamOne = generateRandomScore();
    const goalsTeamTwo = generateRandomScore();

    const result = {
        game:game,
        goalsTeamOne: goalsTeamOne,
        goalsTeamTwo: goalsTeamTwo
    };

    const response = await fetch(
        baseURL + `/results`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(result)
        }
    );

    if (response.ok){
        updateGameResult(game, goalsTeamOne, goalsTeamTwo);
        $('#resultModalAdd').modal('hide');
    }
    else{
        console.log('An error occurred while creating the player');
    }
}

async function fetchGameData(){
    const response = await fetch(baseURL + '/games', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (response.ok) {
       return response.json();
    } else {
        return null;
    }
}

async function updateGameSelector() {
    const gameData = await fetchGameData();

    if (gameData){
        const gameSelector = document.getElementById('gameAdd');
        // Clear existing options
        gameSelector.innerHTML = '';

        // Populate the selector with team names
        gameData.forEach(game => {
            const option = document.createElement('option');
            if(!game.result){
                option.value = game.id; // Assuming your team object has an "id" property
                option.textContent = game.teamOne.name + '   ~~~   ' + game.teamTwo.name; // Assuming your team object has a "name" property
                gameSelector.appendChild(option);
            }
        });
    }
}

$(document).ready(async function () {
    $('#resultModalAdd').on('show.bs.modal', function () {
        updateGameSelector();
    });
    $('.saveResult').on('click', function () {
        createResult();
    });
    fetchResultData();
});
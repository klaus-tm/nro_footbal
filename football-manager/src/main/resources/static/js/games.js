const baseURL = 'http://localhost:8090';
let games = [];

async function getStadiumById(stadiumId) {
    const responseJson = await fetch(
        baseURL + `/stadiums/` + stadiumId,
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

async function deleteGame(id) {
    const response = await fetch(
        baseURL + `/games/` + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

    if (response.ok) {
        games = games.filter(game => game.id !== id); // Remove the deleted team from the teams array
        createGameTable();
    } else {
        console.log("Problem boss");
    }
}

async function updateGame(game, flatpickrInstanceEdit) {
    game.date = flatpickrInstanceEdit.selectedDates[0];
    game.startHour = $('#startHourAdd').val();
    game.stadium = await getStadiumById($('#stadiumAdd').val());
    game.teamOne = await getTeamById($('#teamOneAdd').val());
    game.teamTwo = await getTeamById($('#teamTwoAdd').val());

    const response = await fetch(
        baseURL + `/games` + game.id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(game)
        }
    );

    if (response.ok) {
        fetchGameData(flatpickrInstanceEdit);
    } else {
        console.log('An error occurred while creating the game');
    }
}

function clearFromFieldsGame() {
    $('#gameDateAdd').val('');
    $('#startHourAdd').val('');
    $('#stadiumAdd').val('');
    $('#teamOneAdd').val('');
    $('#teamTwoAdd').val('');

    // Clear fields in the edit game modal
    $('#gameDateEdit').val('');
    $('#startHourEdit').val('');
    $('#stadiumEdit').val('');
    $('#teamOneEdit').val('');
    $('#teamTwoEdit').val('');
}

function editGame(game, flatpickrInstanceEdit) {
    $('#gameModalEdit').modal('show');

    $('#gameDateEdit').val(game.date);
    $('#startHourEdit').val(game.startHour);
    $('#teamOneEdit').val(game.teamOne ? game.teamOne.id : '');
    $('#teamTwoEdit').val(game.teamTwo ? game.teamTwo.id : '');
    $('#stadiumEdit').val(game.stadium ? game.stadium.id : '');


    $('.editGame').on('click', function() {
        updateGame(game, flatpickrInstanceEdit);
        //hide the modal
        $('#gameModalEdit').modal('hide');
        // Clear the form fields
        clearFromFieldsGame();
    });
}

function createGameTable(flatpickrInstanceEdit) {
    const tbody = document.querySelector('.game tbody');
    tbody.innerHTML = ''; // Clear the table body before populating it again

    games.forEach((game) => {
        const row = document.createElement('tr');

        const editButtonCell = document.createElement('td');
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.classList.add('btn', 'btn-primary', 'mr-2');
        editButtonCell.appendChild(editButton);
        row.appendChild(editButtonCell);
        editButton.addEventListener('click', () =>{
            editGame(game, flatpickrInstanceEdit);
        })

        const deleteButtonCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.classList.add('btn', 'btn-danger');
        deleteButtonCell.appendChild(deleteButton);
        row.appendChild(deleteButtonCell);
        deleteButton.addEventListener('click', () =>{
            deleteGame(game.id);
        });

        const dateCell = document.createElement('td');
        dateCell.textContent = game.date;
        row.appendChild(dateCell);

        const startHourCell = document.createElement('td');
        startHourCell.textContent = game.startHour;
        row.appendChild(startHourCell);

        const stadiumCell = document.createElement('td');
        let stadium = game.stadium;
        if(!stadium)
            stadiumCell.textContent = 'none';
        else stadiumCell.textContent = stadium.name;
        row.appendChild(stadiumCell);

        const teamOneCell = document.createElement('td');
        let team = game.teamOne;
        if(!team)
            teamOneCell.textContent = 'none';
        else
            teamOneCell.textContent = team.name;
        row.appendChild(teamOneCell);

        const teamTwoCell = document.createElement('td');
        team = game.teamTwo;
        if(!team)
            teamTwoCell.textContent = 'none';
        else
            teamTwoCell.textContent = team.name;
        row.appendChild(teamTwoCell);

        const resultCell = document.createElement('td');
        let result = game.result;
        if(!result)
            resultCell.textContent = 'not played yet';
        else
            resultCell.textContent = result.goalsTeamOne + '-' + result.goalsTeamTwo;
        row.appendChild(resultCell);

        tbody.appendChild(row);
    });
}

async function fetchGameData(flatpickrInstanceEdit) {
    const response = await fetch(baseURL + '/games', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    games = await response.json();
    if (response.ok) {
        createGameTable(flatpickrInstanceEdit);
    } else {
        console.log('Error fetching game data.');
    }
}

async function createGame(flatpickrInstanceAdd, flatpickrInstanceEdit) {
    console.log('entered here')
    const date = flatpickrInstanceAdd.selectedDates[0];
    const startHour = $('#startHourAdd').val();
    const stadium = await getStadiumById($('#stadiumAdd').val());
    const teamOne = await getTeamById($('#teamOneAdd').val());
    const teamTwo = await getTeamById($('#teamTwoAdd').val());
    const result = null;

    const game = {
        date: date,
        startHour: startHour,
        stadium: stadium,
        teamOne: teamOne,
        teamTwo: teamTwo,
        result: result
    };
    console.log(game)
    const response = await fetch(
        baseURL + `/games`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(game)
        }
    );
    console.log('request sent')
    if (response.ok) {
        fetchGameData(flatpickrInstanceEdit);
        $('#gameModalAdd').modal('hide');
        clearFromFieldsGame();
    } else {
        console.log('An error occurred while creating the game');
    }
}

$(document).ready(async function () {
    const gameDateAdd = document.getElementById('gameDateAdd');
    const gameDateEdit = document.getElementById('gameDateEdit');
    const flatpickrInstanceAdd = flatpickr(gameDateAdd, {
        dateFormat: 'Y-m-d',
        enableTime: false,
        minDate: 'today',
    });
    const flatpickrInstanceEdit = flatpickr(gameDateEdit, {
        dateFormat: 'Y-m-d',
        enableTime: false,
        minDate: 'today',
    });

    $('#gameModalAdd').on('show.bs.modal', function () {
        updateTeamAndStadiumSelectors();
    });

    $('#gameModalEdit').on('show.bs.modal', function () {
        updateTeamAndStadiumSelectors();
    });

    $('.saveGame').on('click', function() {
        createGame(flatpickrInstanceAdd);
    });

    fetchGameData(flatpickrInstanceEdit);
});

async function updateTeamAndStadiumSelectors() {
    const teamData = await fetchTeamData();
    const stadiumData = await fetchStadiumData();

    if (teamData && stadiumData) {
        const teamOneSelectorAdd = document.getElementById('teamOneAdd');
        const teamTwoSelectorAdd = document.getElementById('teamTwoAdd');
        const stadiumSelectorAdd = document.getElementById('stadiumAdd');
        const teamOneSelectorEdit = document.getElementById('teamOneEdit');
        const teamTwoSelectorEdit = document.getElementById('teamTwoEdit');
        const stadiumSelectorEdit = document.getElementById('stadiumEdit');

        // Clear existing options
        teamOneSelectorAdd.innerHTML = '';
        teamTwoSelectorAdd.innerHTML = '';
        stadiumSelectorAdd.innerHTML = '';
        teamOneSelectorEdit.innerHTML = '';
        teamTwoSelectorEdit.innerHTML = '';
        stadiumSelectorEdit.innerHTML = '';

        // Populate the selectors with team and stadium names
        teamData.forEach(team => {
            const option = document.createElement('option');
            option.value = team.id;
            option.textContent = team.name;
            teamOneSelectorAdd.appendChild(option.cloneNode(true));
            teamOneSelectorEdit.appendChild(option);

            const option2 = document.createElement('option');
            option2.value = team.id;
            option2.textContent = team.name;
            teamTwoSelectorAdd.appendChild(option2.cloneNode(true));
            teamTwoSelectorEdit.appendChild(option2)
        });

        stadiumData.forEach(stadium => {
            const option = document.createElement('option');
            option.value = stadium.id;
            option.textContent = stadium.name;
            stadiumSelectorAdd.appendChild(option.cloneNode(true));
            stadiumSelectorEdit.appendChild(option);
        });
    }
}
async function fetchTeamData() {
    const response = await fetch(baseURL + '/teams',{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (response.ok) {
        return response.json();
    } else {
        console.log('Error fetching team data.');
        return null;
    }
}
async function fetchStadiumData() {
    const response = await fetch(
        baseURL + `/stadiums`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });
    if (response.ok) {
        return response.json();
    } else {
        console.log("An error has occurred");
        return null;
    }
}

async function getTeamById(teamId) {
    const responseJson = await fetch(
        baseURL + `/teams/` + teamId,
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

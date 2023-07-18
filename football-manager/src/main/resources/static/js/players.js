const baseURL = 'http://localhost:8090';
let players = [];

$(document).ready(async function () {
    $('#playerModalAdd').on('show.bs.modal', function () {
        updateTeamSelector();
    });
    $('#playerModalEdit').on('show.bs.modal', function () {
        updateTeamSelector();
    });
    fetchPlayerData();
    $('.savePlayer').on('click', function() {
        createPlayer();
    });
});

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

async function updateTeamSelector() {
    const teamData = await fetchTeamData();

    if (teamData) {
        const teamSelectorAdd = document.getElementById('teamAdd');
        const teamSelectorEdit = document.getElementById('teamEdit');
        // Clear existing options
        teamSelectorEdit.innerHTML = '';
        teamSelectorAdd.innerHTML = '';

        // Populate the selector with team names
        teamData.forEach(team => {
            const option = document.createElement('option');
            option.value = team.id; // Assuming your team object has an "id" property
            option.textContent = team.name; // Assuming your team object has a "name" property
            teamSelectorEdit.appendChild(option.cloneNode(true));
            teamSelectorAdd.appendChild(option)
        });
    }
}

async function fetchPlayerData() {
    const response = await fetch(baseURL + '/players', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    players = await response.json();
    if (response.ok) {
        createPlayerTable();
    } else {
        console.log('Error fetching player data.');
    }
}

function createPlayerTable() {
    const tbody = document.querySelector('.player tbody');
    tbody.innerHTML = ''; // Clear the table body before populating it again

    players.forEach((player) => {
        const row = document.createElement('tr');

        const editButtonCell = document.createElement('td');
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.classList.add('btn', 'btn-primary', 'mr-2');
        editButtonCell.appendChild(editButton);
        row.appendChild(editButtonCell);
        editButton.addEventListener('click', () =>{
            editPLayer(player);
        })

        const deleteButtonCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.classList.add('btn', 'btn-danger');
        deleteButtonCell.appendChild(deleteButton);
        row.appendChild(deleteButtonCell);
        deleteButton.addEventListener('click', () =>{
            deletePlayer(player.id);
        });

        const nameCell = document.createElement('td');
        nameCell.textContent = player.name;
        row.appendChild(nameCell);

        const goalsScoredCell = document.createElement('td');
        goalsScoredCell.textContent = player.goalsScored;
        row.appendChild(goalsScoredCell);

        const roleCell = document.createElement('td');
        roleCell.textContent = player.role;
        row.appendChild(roleCell);

        const teamCell = document.createElement('td');
        let team = player.team;
        if(!team)
            teamCell.textContent = 'none';
        else
            teamCell.textContent = team.name;
        row.appendChild(teamCell);

        tbody.appendChild(row);
    });
}

async function createPlayer() {
    const playerName = $('#playerNameAdd').val();
    const goalsScored = parseInt($('#goalsScoredAdd').val());
    const role = $('#roleAdd').val();
    const team = await getTeamById($('#teamAdd').val());

    const player = {
        name: playerName,
        goalsScored: goalsScored,
        role: role,
        team: team
    };

    const response = await fetch(
        baseURL + `/players`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(player)
        }
    );

    if (response.ok){
        fetchPlayerData();
        $('#playerModalAdd').modal('hide');
        clearFromFieldsPlayer();
    }
    else{
        console.log('An error occurred while creating the player');
    }
}

function editPLayer(player) {
    $('#playerModalEdit').modal('show');

    $('#playerNameEdit').val(player.name);
    $('#goalsScoredEdit').val(player.goalsScored);
    $('#roleEdit').val(player.role);
    $('#teamEdit').val(player.team ? player.team.id : '');


    $('.editPlayer').on('click', function() {
        updatePlayer(player);
        //hide the modal
        $('#playerModalEdit').modal('hide');
        // Clear the form fields
        clearFromFieldsPlayer();
    });
}

async function updatePlayer(player) {
    player.name = $('#playerNameEdit').val();
    player.goalsScored = parseInt($('#goalsScoredEdit').val());
    player.role = $('#roleEdit').val();
    player.team = await getTeamById($('#teamEdit').val());

    const response = await fetch(
        baseURL + `/players`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(player)
        }
    );

    if (response.ok){
        fetchPlayerData();
    }
    else{
        console.log('An error occurred while creating the player');
    }
}

async function deletePlayer(id) {
    const response = await fetch(
        baseURL + `/players/` + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

    if (response.ok) {
        players = players.filter(player => player.id !== id); // Remove the deleted team from the teams array
        createPlayerTable();
    } else {
        console.log("Problem boss");
    }
}

function clearFromFieldsPlayer() {
    $('#playerNameAdd').val('');
    $('#goalsScoredAdd').val('0');
    $('#roleAdd').val('');
    $('#teamAdd').val('');

    // Clear fields in the edit player modal
    $('#playerNameEdit').val('');
    $('#goalsScoredEdit').val('0');
    $('#roleEdit').val('');
    $('#teamEdit').val('');
}
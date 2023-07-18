let teams = [];
const baseURL = 'http://localhost:8090';
$(document).ready(async function () {
    fetchTeamData();
    $('.saveTeam').on('click', function() {
        createTeam();
    });
});

async function updateTeam(team) {
    team.name = $('#teamNameEdit').val();
    team.goalsScored = parseInt($('#goalsScoredEdit').val());
    team.goalsReceived = parseInt($('#goalsReceivedEdit').val());
    team.victories = parseInt($('#victoriesEdit').val());
    team.defeats = parseInt($('#defeatsEdit').val());
    team.draws = parseInt($('#drawsEdit').val());

    const response = await fetch(baseURL + '/teams/' + team.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(team)
    });

    if (response.ok) {
        fetchTeamData();
    } else {
        console.log('An error occurred while updating the team');
    }
}

async function editTeam(team) {
    $('#teamModalEdit').modal('show');

    $('#teamNameEdit').val(team.name);
    $('#goalsScoredEdit').val(team.goalsScored);
    $('#goalsReceivedEdit').val(team.goalsReceived);
    $('#victoriesEdit').val(team.victories);
    $('#defeatsEdit').val(team.defeats);
    $('#drawsEdit').val(team.draws);


    $('.editTeam').on('click', function() {
        updateTeam(team);
        //hide the modal
        $('#teamModalEdit').modal('hide');
        // Clear the form fields
        clearFromFieldsTeam();
    });
}

function createTeamTable(container, data) {
    const tbody = document.querySelector('.team tbody');
    tbody.innerHTML = ''; // Clear the table body before populating it again

    teams.forEach((team) => {
        const row = document.createElement('tr');

        const editButtonCell = document.createElement('td');
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.classList.add('btn', 'btn-primary', 'mr-2');
        editButtonCell.appendChild(editButton);
        row.appendChild(editButtonCell);
        editButton.addEventListener('click', () =>{
            editTeam(team);
        })

        const deleteButtonCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.classList.add('btn', 'btn-danger');
        deleteButtonCell.appendChild(deleteButton);
        row.appendChild(deleteButtonCell);
        deleteButton.addEventListener('click', () =>{
            deleteTeam(team.id);
        });

        const nameCell = document.createElement('td');
        nameCell.textContent = team.name;
        row.appendChild(nameCell);

        const goalsScoredCell = document.createElement('td');
        goalsScoredCell.textContent = team.goalsScored;
        row.appendChild(goalsScoredCell);

        const goalsReceivedCell = document.createElement('td');
        goalsReceivedCell.textContent = team.goalsReceived;
        row.appendChild(goalsReceivedCell);

        const victoriesCell = document.createElement('td');
        victoriesCell.textContent = team.victories;
        row.appendChild(victoriesCell);

        const defeatsCell = document.createElement('td');
        defeatsCell.textContent = team.defeats;
        row.appendChild(defeatsCell);

        const drawsCell = document.createElement('td');
        drawsCell.textContent = team.draws;
        row.appendChild(drawsCell);

        tbody.appendChild(row);
    });
}

async function fetchTeamData() {
    const responseJson = await fetch(
        baseURL + `/teams`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });
    teams = await responseJson.json();
    if (responseJson.ok) {
        createTeamTable();
    } else {
        console.log("An error has occurred");
    }
}

async function deleteTeam(id) {
    const response = await fetch(
        baseURL + `/teams/` + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

    if (response.ok) {
        teams = teams.filter(team => team.id !== id); // Remove the deleted team from the teams array
        createTeamTable();
    } else {
        console.log("Problem boss");
    }
}

function clearFromFieldsTeam() {
    $('#teamNameAdd').val('');
    $('#goalsScoredAdd').val(0);
    $('#goalsReceivedAdd').val(0);
    $('#victoriesAdd').val(0);
    $('#defeatsAdd').val(0);
    $('#drawsAdd').val(0);

    $('#teamNameEdit').val('');
    $('#goalsScoredEdit').val(0);
    $('#goalsReceivedEdit').val(0);
    $('#victoriesEdit').val(0);
    $('#defeatsEdit').val(0);
    $('#drawsEdit').val(0);
}

async function createTeam() {
    const teamName = $('#teamNameAdd').val();
    const goalsScored = parseInt($('#goalsScoredAdd').val());
    const goalsReceived = parseInt($('#goalsReceivedAdd').val());
    const victories = parseInt($('#victoriesAdd').val());
    const defeats = parseInt($('#defeatsAdd').val());
    const draws = parseInt($('#drawsAdd').val());

    const team = {
        name: teamName,
        goalsScored: goalsScored,
        goalsReceived: goalsReceived,
        victories: victories,
        defeats: defeats,
        draws: draws
    };

    const response = await fetch(
        baseURL + `/teams`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(team)
    });

    if(response.ok){
        fetchTeamData();
        $('#teamModalAdd').modal('hide');
        clearFromFieldsTeam();
    }
    else {
        console.log('An error occurred while creating the team');
    }
}
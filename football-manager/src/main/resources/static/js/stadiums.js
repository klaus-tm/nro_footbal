let stadiums = [];
const baseURL = 'http://localhost:8090';

async function createStadium() {
    const stadiumName = $('#stadiumNameAdd').val();
    const stadiumLocation = $('#locationAdd').val();

    const stadium = {
        name: stadiumName,
        location: stadiumLocation
    };

    const response = await fetch(
        baseURL + `/stadiums`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(stadium)
        }
    );

    if (response.ok){
        fetchStadiumData();
        $('#stadiumModalAdd').modal('hide');
        clearFromFieldsStadium();
    }
    else{
        console.log('An error occured while creating the stadium');
    }
}

$(document).ready(async function () {
    fetchStadiumData();
    $('.saveStadium').on('click', function() {
        createStadium();
    });
});

async function deleteStadium(id) {
    const response = await fetch(
        baseURL + `/stadiums/` + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

    if (response.ok) {
        stadiums = stadiums.filter(stadium => stadium.id !== id); // Remove the deleted team from the teams array
        createStadiumTable();
    } else {
        console.log("Problem boss");
    }
}

async function updateStadium(stadium) {
    stadium.name = $('#stadiumNameEdit').val();
    stadium.location = $('#locationEdit').val();

    const response = await fetch(baseURL + '/stadiums/' + stadium.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(stadium)
    });

    if (response.ok) {
        fetchStadiumData();
    } else {
        console.log('An error occurred while updating the stadium');
    }
}

function editStadium(stadium) {
    $('#stadiumModalEdit').modal('show');

    $('#stadiumNameEdit').val(stadium.name);
    $('#locationEdit').val(stadium.location);

    $('.editStadium').on('click', function() {
        updateStadium(stadium);
        //hide the modal
        $('#stadiumModalEdit').modal('hide');
        // Clear the form fields
        clearFromFieldsStadium();
    });
}

function createStadiumTable() {
    const tbody = document.querySelector('.stadium tbody');
    tbody.innerHTML = ''; // Clear the table body before populating it again

    stadiums.forEach((stadium) => {
        const row = document.createElement('tr');

        const editButtonCell = document.createElement('td');
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.classList.add('btn', 'btn-primary', 'mr-2');
        editButtonCell.appendChild(editButton);
        row.appendChild(editButtonCell);
        editButton.addEventListener('click', () =>{
            editStadium(stadium);
        })

        const deleteButtonCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.classList.add('btn', 'btn-danger');
        deleteButtonCell.appendChild(deleteButton);
        row.appendChild(deleteButtonCell);
        deleteButton.addEventListener('click', () =>{
            deleteStadium(stadium.id);
        });

        const nameCell = document.createElement('td');
        nameCell.textContent = stadium.name;
        row.appendChild(nameCell);

        const locationCell = document.createElement('td');
        locationCell.textContent = stadium.location;
        row.appendChild(locationCell);

        tbody.appendChild(row);
    });
}

async function fetchStadiumData() {
    const responseJson = await fetch(
        baseURL + `/stadiums`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });
    stadiums = await responseJson.json();
    if (responseJson.ok) {
        createStadiumTable();
    } else {
        console.log("An error has occurred");
    }
}

function clearFromFieldsStadium() {
    $('#stadiumNameAdd').val('');
    $('#locationAdd').val("");

    $('#stadiumNameEdit').val('');
    $('#locationEdit').val("");
}
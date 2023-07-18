const baseURL = 'http://localhost:8090';
// Step 1: Fetch Team and Result Data
async function fetchTeamData() {
    const response = await fetch(
        baseURL + `/teams`,
        {
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

function sortTeamsByVictories(teams) {
    return teams.slice().sort((a, b) => b.victories - a.victories);
}

function sortTeamsByGoalsRate(teams) {
    return teams.slice().sort((a, b) => (b.goalsScored / b.goalsReceived) - (a.goalsScored / a.goalsReceived));
}


// Step 4: Display Top 5 Teams
function displayTop5TeamsVictories(teams) {
    const tableBody = document.getElementById('top5TeamsTableBodyVictories');
    tableBody.innerHTML = ''; // Clear the table body before populating it again

    teams.forEach((team, index) => {
        const row = document.createElement('tr');

        const rankCell = document.createElement('td');
        rankCell.textContent = index + 1; // Display the rank of the team (1-based index)
        rankCell.classList.add('wrapped-cell');
        row.appendChild(rankCell);

        const nameCell = document.createElement('td');
        nameCell.textContent = team.name; // Display the team name
        nameCell.classList.add('wrapped-cell');
        row.appendChild(nameCell);

        const victoriesCell = document.createElement('td');
        victoriesCell.textContent = team.victories; // Display the number of victories
        victoriesCell.classList.add('wrapped-cell');
        row.appendChild(victoriesCell);

        const goalsRateCell = document.createElement('td');
        const goalsRate = team.goalsScored / team.goalsReceived;
        goalsRateCell.textContent = goalsRate.toFixed(2); // Display the goals rate with 2 decimal places
        goalsRateCell.classList.add('wrapped-cell');
        row.appendChild(goalsRateCell);

        tableBody.appendChild(row);
    });
}

function displayTop5TeamsGoalRates(teams) {
    const tableBody = document.getElementById('top5TeamsTableBodyGoalRates');
    tableBody.innerHTML = ''; // Clear the table body before populating it again

    teams.forEach((team, index) => {
        const row = document.createElement('tr');

        const rankCell = document.createElement('td');
        rankCell.textContent = index + 1; // Display the rank of the team (1-based index)
        rankCell.classList.add('wrapped-cell');
        row.appendChild(rankCell);

        const nameCell = document.createElement('td');
        nameCell.textContent = team.name; // Display the team name
        nameCell.classList.add('wrapped-cell');
        row.appendChild(nameCell);

        const goalsRateCell = document.createElement('td');
        const goalsRate = team.goalsScored / team.goalsReceived;
        goalsRateCell.textContent = goalsRate.toFixed(2); // Display the goals rate with 2 decimal places
        goalsRateCell.classList.add('wrapped-cell');
        row.appendChild(goalsRateCell);

        const victoriesCell = document.createElement('td');
        victoriesCell.textContent = team.victories; // Display the number of victories
        victoriesCell.classList.add('wrapped-cell');
        row.appendChild(victoriesCell);

        tableBody.appendChild(row);
    });
}
// Main Function to Generate Championship Statistics
async function generateChampionshipStatistics() {
    const teams = await fetchTeamData();

    const top5TeamsByVictories = sortTeamsByVictories(teams).slice(0, 5);
    displayTop5TeamsVictories(top5TeamsByVictories);

    const top5TeamsByGoalsRate = sortTeamsByGoalsRate(teams).slice(0, 5);
    displayTop5TeamsGoalRates(top5TeamsByGoalsRate);
}
$(document).ready(function () {
    generateChampionshipStatistics();
});

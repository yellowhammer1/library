import { showResult } from './app.js';

const formGetAuthor = document.getElementById('getAuthor');
const formGetAuthors = document.getElementById('getAuthors');
const formPostAuthor = document.getElementById('postAuthor');
const formPutAuthor = document.getElementById('putAuthor');
const formDeleteAuthor = document.getElementById('deleteAuthor');

formGetAuthor.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formGetAuthor);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid author ID');
        return;
    }

    fetch(`http://localhost:8080/authors/${id}`, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => {
                    throw new Error(`${error}`);
                });
            }
            return response.json();
        })
        .then(author => {
            console.log(author);
            showResult("GET author", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(author).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        ${Object.values(author).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET author (error)", `Cannot get author: ${error.message}`);
        });
});

formGetAuthors.addEventListener('submit', async event => {
    event.preventDefault();

    fetch(`http://localhost:8080/authors`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }})
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => {
                    throw new Error(`${error}`);
                });
            }
            return response.json();
        })
        .then(authors => {
            console.log(authors);
            if (authors.length === 0) {
                showResult("GET authors", `No authors found`);
                return;
            }
            showResult("GET authors", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(authors[0]).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                ${authors.map(author => `
                    <tr>
                        ${Object.values(author).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                    `).join('')}
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET author (error)", `Cannot get author: ${error.message}`);
        });
});

formPostAuthor.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPostAuthor);
    console.log(JSON.stringify({
        name: formData.get('name'),
        birthdate: formData.get('birthdate'),
        nationality: formData.get('nationality'),
        bookIDs: formData.get('book IDs').split(',').map(id => parseInt(id))
    }));

    fetch(`http://localhost:8080/authors`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: formData.get('name'),
            birthdate: formData.get('birthdate'),
            nationality: formData.get('nationality'),
            bookIDs: formData.get('book IDs').split(',').map(id => parseInt(id))
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => {
                    throw new Error(`${error}`);
                });
            }
            return response.json();
        })
        .then(author => {
            console.log(author);
            showResult("POST author", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(author).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(author).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("POST author (error)", `Cannot post author: ${error.message}`);
        });
});

formPutAuthor.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPutAuthor);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid author ID');
        return;
    }

    fetch(`http://localhost:8080/authors/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: formData.get('id'),
            name: formData.get('name'),
            birthdate: formData.get('birthdate'),
            nationality: formData.get('nationality'),
            bookIDs: formData.get('book IDs').split(',').map(id => parseInt(id))
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => {
                    throw new Error(`${error}`);
                });
            }
            return response.json();
        })
        .then(author => {
            console.log(author);
            showResult("PUT author", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(author).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(author).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("PUT author (error)", `Cannot put author: ${error.message}`);
        });
});

formDeleteAuthor.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formDeleteAuthor);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid journalist ID');
        return;
    }

    fetch(`http://localhost:8080/authors/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(error => {
                    throw new Error(`${error}`);
                });
            }
            return response;
        })
        .then(author => {
            console.log(author);
            showResult("DELETE author", "Successfully deleted author");
        })
        .catch(error => {
            showResult("DELETE author (error)", `Cannot delete author: ${error.message}`);
        });
});
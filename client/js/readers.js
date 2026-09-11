import { showResult } from './app.js';

const formGetReader = document.getElementById('getReader');
const formGetReaders = document.getElementById('getReaders');
const formPostReader = document.getElementById('postReader');
const formPutReader = document.getElementById('putReader');
const formDeleteReader = document.getElementById('deleteReader');

formGetReader.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formGetReader);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid reader ID');
        return;
    }

    fetch(`http://localhost:8080/readers/${id}`, {
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
        .then(reader => {
            console.log(reader);
            showResult("GET reader", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(reader).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        ${Object.values(reader).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET reader (error)", `Cannot get reader: ${error.message}`);
        });
});

formGetReaders.addEventListener('submit', async event => {
    event.preventDefault();

    fetch(`http://localhost:8080/readers`, {
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
        .then(readers => {
            console.log(readers);
            if (readers.length === 0) {
                showResult("GET readers", `No readers found`);
                return;
            }
            showResult("GET readers", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(readers[0]).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                ${readers.map(reader => `
                    <tr>
                        ${Object.values(reader).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                    `).join('')}
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET reader (error)", `Cannot get reader: ${error.message}`);
        });
});

formPostReader.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPostReader);
    console.log(JSON.stringify({
        name: formData.get('name'),
        email: formData.get('email'),
        phoneNumber: formData.get('phone number'),
        birthdate: formData.get('birthdate'),
        registered: formData.get('registered')
    }));

    fetch(`http://localhost:8080/readers`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: formData.get('name'),
            email: formData.get('email'),
            phoneNumber: formData.get('phone number'),
            birthdate: formData.get('birthdate'),
            registered: formData.get('registered')
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
        .then(reader => {
            console.log(reader);
            showResult("POST reader", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(reader).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(reader).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("POST reader (error)", `Cannot post reader: ${error.message}`);
        });
});

formPutReader.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPutReader);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid reader ID');
        return;
    }

    fetch(`http://localhost:8080/readers/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: formData.get('id'),
            name: formData.get('name'),
            email: formData.get('email'),
            phoneNumber: formData.get('phone number'),
            birthdate: formData.get('birthdate'),
            registered: formData.get('registered')
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
        .then(reader => {
            console.log(reader);
            showResult("PUT reader", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(reader).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(reader).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("PUT reader (error)", `Cannot put reader: ${error.message}`);
        });
});

formDeleteReader.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formDeleteReader);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid journalist ID');
        return;
    }

    fetch(`http://localhost:8080/readers/${id}`, {
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
        .then(reader => {
            console.log(reader);
            showResult("DELETE reader", "Successfully deleted reader");
        })
        .catch(error => {
            showResult("DELETE reader (error)", `Cannot delete reader: ${error.message}`);
        });
});
import { showResult } from './app.js';

const formGetLoan = document.getElementById('getLoan');
const formGetLoans = document.getElementById('getLoans');
const formPostLoan = document.getElementById('postLoan');
const formPutLoan = document.getElementById('putLoan');
const formDeleteLoan = document.getElementById('deleteLoan');

formGetLoan.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formGetLoan);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid loan ID');
        return;
    }

    fetch(`http://localhost:8080/loans/${id}`, {
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
        .then(loan => {
            console.log(loan);
            showResult("GET loan", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(loan).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        ${Object.values(loan).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET loan (error)", `Cannot get loan: ${error.message}`);
        });
});

formGetLoans.addEventListener('submit', async event => {
    event.preventDefault();

    fetch(`http://localhost:8080/loans`, {
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
        .then(loans => {
            console.log(loans);
            if (loans.length === 0) {
                showResult("GET loans", `No loans found`);
                return;
            }
            showResult("GET loans", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(loans[0]).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                ${loans.map(loan => `
                    <tr>
                        ${Object.values(loan).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                    `).join('')}
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET loan (error)", `Cannot get loan: ${error.message}`);
        });
});

formPostLoan.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPostLoan);
    console.log(JSON.stringify({
        borrowed: formData.get('borrowed'),
        returned: formData.get('returned'),
        reader: formData.get('reader ID'),
        book: formData.get('book ID')
    }));

    fetch(`http://localhost:8080/loans`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            borrowed: formData.get('borrowed'),
            returned: formData.get('returned'),
            readerId: formData.get('reader ID'),
            bookId: formData.get('book ID')
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
        .then(loan => {
            console.log(loan);
            showResult("POST loan", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(loan).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(loan).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("POST loan (error)", `Cannot post loan: ${error.message}`);
        });
});

formPutLoan.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPutLoan);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid loan ID');
        return;
    }

    fetch(`http://localhost:8080/loans/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: formData.get('id'),
            borrowed: formData.get('borrowed'),
            returned: formData.get('returned'),
            readerId: formData.get('reader ID'),
            bookId: formData.get('book ID')
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
        .then(loan => {
            console.log(loan);
            showResult("PUT loan", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(loan).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(loan).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("PUT loan (error)", `Cannot put loan: ${error.message}`);
        });
});

formDeleteLoan.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formDeleteLoan);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid journalist ID');
        return;
    }

    fetch(`http://localhost:8080/loans/${id}`, {
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
        .then(loan => {
            console.log(loan);
            showResult("DELETE loan", "Successfully deleted loan");
        })
        .catch(error => {
            showResult("DELETE loan (error)", `Cannot delete loan: ${error.message}`);
        });
});
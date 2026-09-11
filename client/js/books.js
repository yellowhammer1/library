import { showResult } from './app.js';

const formGetBook = document.getElementById('getBook');
const formGetBooks = document.getElementById('getBooks');
const formPostBook = document.getElementById('postBook');
const formPutBook = document.getElementById('putBook');
const formDeleteBook = document.getElementById('deleteBook');

formGetBook.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formGetBook);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid book ID');
        return;
    }

    fetch(`http://localhost:8080/books/${id}`, {
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
        .then(book => {
            console.log(book);
            showResult("GET book", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(book).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        ${Object.values(book).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET book (error)", `Cannot get book: ${error.message}`);
        });
});

formGetBooks.addEventListener('submit', async event => {
    event.preventDefault();

    fetch(`http://localhost:8080/books`, {
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
        .then(books => {
            console.log(books);
            if (books.length === 0) {
                showResult("GET books", `No books found`);
                return;
            }
            showResult("GET books", `
            <table>
                <thead>
                    <tr>
                        ${Object.keys(books[0]).map(key => `<th>${key}</th>`).join('')}
                    </tr>
                </thead>
                <tbody>
                ${books.map(book => `
                    <tr>
                        ${Object.values(book).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                    </tr>
                    `).join('')}
                </tbody>
            `);
        })
        .catch(error => {
            showResult("GET book (error)", `Cannot get book: ${error.message}`);
        });
});

formPostBook.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPostBook);
    console.log(JSON.stringify({
        name: formData.get('name'),
        numberOfCopies: formData.get('number of copies'),
        isbn: formData.get('isbn'),
        published: formData.get('published'),
        authorIDs: formData.get('author IDs').split(',').map(id => parseInt(id))
    }));

    fetch(`http://localhost:8080/books`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: formData.get('name'),
            numberOfCopies: formData.get('number of copies'),
            isbn: formData.get('isbn'),
            published: formData.get('published'),
            authorIDs: formData.get('author IDs').split(',').map(id => parseInt(id))
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
        .then(book => {
            console.log(book);
            showResult("POST book", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(book).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(book).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("POST book (error)", `Cannot post book: ${error.message}`);
        });
});

formPutBook.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formPutBook);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid book ID');
        return;
    }

    fetch(`http://localhost:8080/books/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: formData.get('id'),
            name: formData.get('name'),
            numberOfCopies: formData.get('number of copies'),
            isbn: formData.get('isbn'),
            published: formData.get('published'),
            authorIDs: formData.get('author IDs').split(',').map(id => parseInt(id))
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
        .then(book => {
            console.log(book);
            showResult("PUT book", `
        <table>
            <thead>
                <tr>
                    ${Object.keys(book).map(key => `<th>${key}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                <tr>
                    ${Object.values(book).map(value => `<td>${value === null || value === undefined ? '' : value}</td>`).join('')}
                </tr>
            </tbody>
        `);
        })
        .catch(error => {
            showResult("PUT book (error)", `Cannot put book: ${error.message}`);
        });
});

formDeleteBook.addEventListener('submit', async event => {
    event.preventDefault();

    const formData = new FormData(formDeleteBook);
    const id = formData.get('id');

    if (!formData.has('id') || isNaN(id)) {
        alert('Please, enter a valid journalist ID');
        return;
    }

    fetch(`http://localhost:8080/books/${id}`, {
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
        .then(book => {
            console.log(book);
            showResult("DELETE book", "Successfully deleted book");
        })
        .catch(error => {
            showResult("DELETE book (error)", `Cannot delete book: ${error.message}`);
        });
});
export function showResult(title, result) {
    document.getElementById('results').getElementsByTagName("h3")[0].innerHTML = title;
    document.getElementById('results').getElementsByTagName("div")[0].innerHTML = result;
};
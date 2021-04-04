$(document).ready(function () {
    allBooks();

    $('#searchButton').click(showTabl);
    $('#allButton').click(allBooks);

    function allBooks() {
        $.ajax({
            type: 'GET',
            url: '/get-books',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (res) {
                $('#tableBook').html(searchResults(res));

            },
            error: function (response) {
                console.log(response);
            }
        })
    }

    function showTabl() {
        $.ajax({
            type: 'GET',
            url: '/search-books',
            dataType: 'json',
            data: {
                getBy: $("#searchInput").val()
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (res) {
                $("#searchInput").val("");
                $('#tableBook').html(searchResults(res));
            },
            error: function (response) {
                console.log(response);
            }
        })
    }
    function showErrors(res) {
    let r = "";
    for(let i=0; i<res.length;i++){
        r+=res[i];
    }
    return r;
    }

    function searchResults(res){
        let tabl = "<table class='table table-hover'>"+
            "<thead class='table-dark'>"+
            "<th>Isbn</th>"+
            "<th>Title</th>"+
            "<th>Author</th>"+
            "<th></th>"+
            "<th></th>"+
            "</thead>"+
            "<tbody>";

        for(let i=0; i<res.length; i++){

            tabl +=  "<tr>"+
                "<td>"+res[i].isbn+"</td>"+
                "<td>"+res[i].title+"</td>"+
                "<td>"+res[i].author+"</td>"+
                "<td ><a href=/book/"+res[i].isbn.replace(" ","_")+">View</a></td>"+
                "<td><a href='/add-fav/"+res[i].isbn+"' class='btn btn-outline-dark'><i class='fas fa-star bg-warning'></i>Add to fav</a></td>"+
                "</tr>";
        }

        tabl += "</tbody></table>";
        return tabl;
    }

    $('#addButton').click(function (event){
        event.preventDefault();
        let book = {
            isbn: $("#isbn").val(),
            title: $("#title").val(),
            author: $("#author").val()
        }
        console.log(book);

        $.ajax({
            type: 'POST',
            url: '/add-book',

            data: JSON.stringify(book),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response) {
                console.log(response);
                //showTabl();

                $("#isbn").val("");
                $("#title").val("");
                $("#author").val("");
                window.location='/'
            },
            error: function (response) {
              alert(response.responseText);
                $("#isbn").val("");
                $("#title").val("");
                $("#author").val("");
                console.log(response.responseText.length);
            }
        })
    });
});
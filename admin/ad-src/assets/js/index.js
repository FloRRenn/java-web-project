var movieList = []

$(async () => {
    $('.preloader').fadeIn(100);
    
    await GetList()
    $('.preloader').fadeOut(1000);
})

$(".ticket-search-form").on('submit', (e) => e.preventDefault());

$('#search-val').on('input', function() {
    RenderMovieList();
});


$("#modal-user-delete").click(async () => {
    let username = $("#user-modal-container #user-name").text()

    let result = await Swal.fire({
        title: 'Chắc chắn chứ?',
        text: "Hành động này sẽ không thể hoàn tác",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Vâng, tôi chắc chắn',
        cancelButtonText: 'Hủy bỏ',
    })

    if (!result.isConfirmed) 
        return;

    let response = await PenguRequestAPI( 'DELETE','api/user/delete?username=' + encodeURI(username), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (!response)
        return

    Swal.fire({
        position: 'top-end',
        text: response.message,
        backdrop : false,
        showConfirmButton: false,
        timer: 3000
      })

    await GetList()
})

$("#modal-user-giveadmin").click(async () => {
    let username = $("#user-modal-container #user-name").text()

    let response = await PenguRequestAPI( 'GET','api/user/giveadmin?username=' + encodeURI(username), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (!response)
        return

    Swal.fire({
        position: 'top-end',
        text: response.message,
        backdrop : false,
        showConfirmButton: false,
        timer: 3000
      })

    await GetList()
})

$("#modal-user-removeadmin").click(async () => {
    let username = $("#user-modal-container #user-name").text()

    let response = await PenguRequestAPI( 'GET','api/user/removeadmin?username=' + encodeURI(username), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (!response)
        return

    Swal.fire({
        position: 'top-end',
        text: response.message,
        backdrop : false,
        showConfirmButton: false,
        timer: 3000
      })

    await GetList()
})

async function GetList() {  
    let list = await PenguRequestAPI( 'GET','api/user/getall', {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (list)
        movieList = list;

    RenderMovieList()
}

function RenderMovieList() {
    $('#user-modal-container').modal('hide')
    const container = $("#body-container")
    container.empty()

    let data = movieList.filter((m) => m.username.indexOf($('#search-val').val()) != -1)

    for (let i = 0; i < data.length; i++) {
        let user = data[i]

        if (user) {
            let item = $(`
            <tr>
                <th scope="row" id="user-id"></th>
                <td id="user-name"></td>
                <td id="user-email"></td>
                <td id="user-status"></td>
                <td id="user-role"></td>
                <td> <button type="button" class="btn btn-info" id="user-modal">Info</button> </td>
            </tr>
            `)

            item.find("#user-id").text(user.id)
            item.find("#user-name").text(user.username)
            item.find("#user-email").text(user.email)
            item.find("#user-status").text(user.status)
            item.find("#user-role").text(user.roles.map(d => d.replace(/^ROLE_/g, "")).join(", "))
            item.find("#user-modal").attr("onclick", `ShowModalUser('${formatContentHTML(user.id)}');`)

            container.append(item)
            
        }
    }
}

async function ShowModalUser(id) {
    let user = movieList.find(d => d.id == id)
    if (!user)  return;
    const container =  $('#user-modal-container')

    container.find("#user-id").text(user.id)
    container.find("#user-name").text(user.username)
    container.find("#user-status").text(user.status)
    container.find("#user-fullname").text(user.fullname)
    container.find("#user-email").text(user.email)
    container.find("#user-phone").text(user.phone)
    container.find("#user-address").text(user.address)
    container.find("#user-role").text(user.roles.map(d => d.replace(/^ROLE_/g, "")).join(", "))
    container.find("#user-updateat").text(GetTimeFormat(new Date(user.updateAt)))
    container.find("#user-createat").text(GetTimeFormat(new Date(user.createAt)))

    container.modal('show')
}

function GetTimeFormat(time) {
    return time.toString()
}
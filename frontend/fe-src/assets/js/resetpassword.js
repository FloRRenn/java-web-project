
$(async function() {
    let token = getParam('code')

    if (!token)
        window.location.href = ('/')

    let res = await PenguRequestAPI('GET', 'api/user/recovery?code=' + encodeURI(token), {}, {}).then(r => r.json()).catch(error => {console.log(error); return false})

    if (!res || !res.message) {
        window.location.href = ('/')
    }

    if (res.message != 'Token is valid')
        window.location.href = ('/')

    $('.preloader').fadeOut(1000);
})

$(".account-form").on( "submit", async function( event ) {
    event.preventDefault();
    const form = $(this)

    $(".validationMessage").remove()

    let token = getParam('code')
    let password = form.find('input[id="password"]').val()
    let passwordConfirm = form.find('input[id="passwordConfirm"]').val()

    // Check Validate for password
    if (! /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$^+=!*()@%&_-]).{8,}$/.test(password))
        return $('#password').after(`<span class='validationMessage' style='color:red;'>Mật khẩu không hợp lệ. <br>Yêu cầu:<br>&emsp;+ Tối thiểu 8 kí tự<br>&emsp;+ Có ít nhất 1 chữ cái in hoa, 1 chữ cái thường và 1 kí tự đặc biệt (@#$%^&+=!-_)</span>`);
        
    // Check for confirm password
    if (password != passwordConfirm) 
        return $('#passwordConfirm').after(`<span class='validationMessage' style='color:red;'>Mật khẩu không trùng khớp.</span>`);
    
    // Loading wait to response
    $('.preloader').fadeIn(100);

    //Request to Backend
    let res = await PenguRequestAPI('POST', 'api/user/recovery?code=' + encodeURI(token),
    {
        "body" : {
            "password": password,
        }
    },
    { "Content-Type": "application/json"}).then(r => r.json()).catch(error => {console.log(error); return false})

    //Hide loading
    $('.preloader').fadeOut(100);

    // Bad Connection
    if (!res || !res.message) {
        return Swal.fire("Có lỗi kết nối vui lòng thử lại.");
    }
    
    console.log(res)

    if (res.message != 'Set new password') 
        return window.location.href = ('/')

    //Not thing may be success
    await Swal.fire({
        icon: 'success',
        title: 'Đổi mật khẩu thành công'
    })


    window.location.href = ('/login')
});
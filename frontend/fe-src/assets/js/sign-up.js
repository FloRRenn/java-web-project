$(async function() {
    if (await TokenIsVaild())
        window.location.href = ('/')
    $('.preloader').fadeOut(1000);
})


$(".account-form").on( "submit", async function( event ) {
    event.preventDefault();
    const form = $(this)

    let fullname = form.find('input[id="fullname"]').val()
    let address = form.find('input[id="address"]').val()
    let phone = form.find('input[id="phone"]').val()
    let email = form.find('input[id="email"]').val()
    let username = form.find('input[id="username"]').val().toLowerCase()
    let password = form.find('input[id="password"]').val()
    let passwordConfirm = form.find('input[id="passwordConfirm"]').val()

    $(".validationMessage").remove()

    // Check Validate for phone
    if (! /(^(84|0)[3|5|7|8|9])+([0-9]{8})\b/.test(phone))
        return $('#phone').after(`<span class='validationMessage' style='color:red;'>Số điện thoại bạn nhập không hợp lệ.</span>`);

    // Check Validate for email
    if (! /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))
        return $('#email').after(`<span class='validationMessage' style='color:red;'>Email bạn nhập không hợp lệ.</span>`);

    // Check Validate for username
    if (! /^[a-z0-9_-]{3,16}$/.test(username))
       return $('#username').after(`<span class='validationMessage' style='color:red;'>Tên đăng nhập không hợp lệ. <br>Yêu cầu: Chỉ chấp nhận a-z, 0-9, "-", "_" và có độ dài từ 3-16 kí tự</span>`);
   
    // Check Validate for password
    if (! /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$^+=!*()@%&_-]).{8,}$/.test(password))
        return $('#password').after(`<span class='validationMessage' style='color:red;'>Mật khẩu không hợp lệ. <br>Yêu cầu:<br>&emsp;+ Tối thiểu 8 kí tự<br>&emsp;+ Có ít nhất 1 chữ cái in hoa, 1 chữ cái thường và 1 kí tự đặc biệt (@#$%^&+=!-_)</span>`);
        
    // Check for confirm password
    if (password != passwordConfirm) 
        return $('#passwordConfirm').after(`<span class='validationMessage' style='color:red;'>Mật khẩu không trùng khớp.</span>`);
    
    // Check for accept ...
    if (!form.find('input[id="bal"]').is(":checked"))
        return $('#bal2').after(`<span class='validationMessage' style='color:red;'>Vui lòng chấp nhận các điều khoản này.</span>`);

    // Loading wait to response
    $('.preloader').fadeIn(100);

    //Request to Backend
    let res = await PenguRequestAPI('POST', 'api/auth/signup',
    {
        "body" : {
            "fullname": fullname,
            "username": username,
            "password": password,
            "email": email,
            "address": address,
            "phone": phone
        }
    },
    { "Content-Type": "application/json"}).then(r => r.json()).catch(error => {console.log(error); return false})

    //Hide loading
    $('.preloader').fadeOut(100);

    // Bad Connection
    if (!res) {
        return Swal.fire("Có lỗi kết nối vui lòng thử lại.");
    }
    // BAD Request
    if (res.status && res.status == 'BAD_REQUEST') {
        switch (res.message) {
            case 'Username is existed':
                return $('#username').after(`<span class='validationMessage' style='color:red;'>Tên đăng nhập đã tồn tại</span>`);
            case 'Email is existed':
                return $('#email').after(`<span class='validationMessage' style='color:red;'>Email đã tồn tại</span>`);
            default:
                return Swal.fire("Có lỗi xảy ra. Error code: " + (res.message));
        }
        return
    }

    console.log(res)

    //Not thing may be success
    await Swal.fire({
        icon: 'success',
        title: 'Đăng kí thành công',
        text: 'Một email đã được gửi đến bạn vui lòng kiểm tra hộp thư để xác minh danh tính tài khoản!',
    })


    // Redirect to /login
    window.location.href = ('/login')
});
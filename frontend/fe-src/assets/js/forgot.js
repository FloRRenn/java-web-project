
$(async function() {
    $('.preloader').fadeOut(1000);
})

$(".account-form").on( "submit", async function( event ) {
    event.preventDefault();
    const form = $(this)

    $(".validationMessage").remove()

    let username = form.find('input[id="email"]').val()
    // if (! /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
    //     return $('#email').after(`<span class='validationMessage' style='color:red;'>Email bạn nhập không hợp lệ.</span>`);
    // }
        
    // Loading wait to response
    $('.preloader').fadeIn(100);

    //Request to Backend
    let res = await PenguRequestAPI('GET', 'api/user/forgotpassword?username=' + encodeURI(username), {}, {}).then(r => r.json()).catch(error => {console.log(error); return false})

    //Hide loading
    $('.preloader').fadeOut(100);

    // Bad Connection
    if (!res || !res.message) {
        return Swal.fire("Có lỗi kết nối vui lòng thử lại.");
    }
    
    console.log(res)
    
    if (res.message && res.message == 'User not found') {
        return Swal.fire({
            icon: 'error',
            title: 'Thông báo',
            text: 'User không tồn tại!',
        })
    
    }

    await Swal.fire({
        icon: 'success',
        title: 'Vui lòng kiểm tra mail để lấy lại mật khẩu'
    })

    window.location.href = ('/')
});
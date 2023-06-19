$(async function() {
    $('.preloader').fadeIn(100);
    let paymentId = getParam('vnp_TxnRef')
    if (!paymentId)
        return window.location.href = ('/')

    let res = await PenguRequestAPI('POST', `api/payment/${paymentId}/verify`, {}, { "Content-Type": "application/json"}, true).then(r => r.json()).catch(error => {console.log(error); return false})

    if (!res || !res.status)
        $(".message").text("Giao dịch không tồn tại hoặc đã hoàn tất")

    if (res.status == 'PENDING') 
        $(".message").text("Giao dịch của bạn đang trong quá trình xử lý.")

    if (res.status == 'UNPAID') 
        $(".message").text("Giao dịch của bạn đã bị hủy.")
     
    if (res.status == 'PAID') 
        $(".message").text("Giao dịch của bạn đã thành công. Vé của bạn sẽ được gửi qua email đăng kí.")


    $('.preloader').fadeOut(1000);
})


function GetTimeFormat(time) {
    return `${time.getHours().toString().padStart(2, '0')}:${time.getMinutes().toString().padStart(2, '0')} - ${time.getDate().toString().padStart(2, '0')}/${(time.getMonth() + 1).toString().padStart(2, '0')}`
}
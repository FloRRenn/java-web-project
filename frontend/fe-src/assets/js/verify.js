$(async function() {
    $('.preloader').fadeIn(100);
    let code = getParam('code')
    if (!code)
        return window.location.href = ('/')

    await PenguRequestAPI('GET', `api/auth/verify/${encodeURIComponent(code)}`, {}, {}, false).then(r => r.json()).catch(error => {console.log(error); return false})

    $('.preloader').fadeOut(1000);
})



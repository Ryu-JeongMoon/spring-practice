function openPass() {
  window.open('', 'popupChk',
    'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, ' +
    'status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
  document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
  document.form_chk.target = "popupChk";
  document.form_chk.submit();
}

document.addEventListener("DOMContentLoaded", function() {
  document.getElementById("open-pass-btn").addEventListener("click", openPass);
});

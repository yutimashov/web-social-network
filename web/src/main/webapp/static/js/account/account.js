window.addEventListener("DOMContentLoaded", function(event){
    document.querySelectorAll('button.btn-delete-account-wall-msg').forEach(el => {
        el.onclick = () => deleteItem(el)
    })
});
function deleteItem(button){
    const messageDiv = button.closest('.account-wall-msg');
    if (messageDiv) {
        messageDiv.remove();
    }
}
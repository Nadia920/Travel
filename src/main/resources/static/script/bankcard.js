$(document).ready(function ($) {
    $('#x_card_code').mask('999');
    $('#cc-exp').mask('99/99');
    $('#cc-number').mask('9999 9999 9999 9999');


$("#payment-button").click(function(e) {

    // Fetch form to apply Bootstrap validation
    var form = $(this).parents('form');

    if (form[0].checkValidity() === false) {
        e.preventDefault();
        e.stopPropagation();
    }
    else {
        // Perform ajax submit here
        form.submit();
    }

    form.addClass('was-validated');
});

});
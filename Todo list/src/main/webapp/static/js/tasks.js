window.addEventListener('load', () => {

    if (document.getElementById("table") !== null) {

        document.getElementById("table").addEventListener('click', function (event) {
            let target = event.target;

            if (target.alt === 'CHECKED' || target.alt === 'UNCHECKED') {

                let id = target.parentElement.parentElement.lastElementChild.textContent;

                let form = document.getElementById("status");
                form.firstElementChild.value = id.trim();
                form.firstElementChild.nextElementSibling.value = target.alt;
                form.submit();

            } else if (target.alt === 'delete') {
                let id = target.parentElement.nextElementSibling.textContent;

                let form = document.getElementById("delete");
                form.firstElementChild.value = id.trim();
                form.submit();
            }
        })
    }
});
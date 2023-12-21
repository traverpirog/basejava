const accordions = document.querySelectorAll('.accordion');
accordions && accordions.forEach(accordion => {
    const items = accordion.querySelectorAll('.accordion-item');
    items && items.forEach(item => {
        const handler = item.querySelector('.accordion-item__handler');
        const content = item.querySelector('.accordion-item__content');
        (handler && content) && handler.addEventListener('click', () => {
            handler.classList.toggle('accordion-item__handler--active');
            content.classList.toggle('accordion-item__content--active');
        });
    });
});

const handleAddCompany = (e) => {
    const companyWrapper = e.currentTarget.parentElement;
    const companyItem = companyWrapper.querySelector('.form-label--company');
    const companyItemNew = companyItem.cloneNode(true);
    const formInputs = companyItemNew.querySelectorAll('.form-input');
    formInputs.forEach(input => input.value = '');
    companyWrapper.insertBefore(companyItemNew, e.currentTarget);

    // После добавления компании, повторно настраиваем обработчики для периодов
    const periodHandlers = companyItemNew.querySelectorAll('.add-period-js');
    periodHandlers.forEach(periodHandler => {
        periodHandler.addEventListener('click', handleAddPeriod);
    });
};

const handleAddPeriod = (e) => {
    const periodWrapper = e.currentTarget.parentElement;
    const periodItem = periodWrapper.querySelector('.form-period');
    const periodItemNew = periodItem.cloneNode(true);
    const formInputs = periodItemNew.querySelectorAll('.form-input');
    formInputs.forEach(input => input.value = '');
    periodWrapper.insertBefore(periodItemNew, e.currentTarget);
};

const formListCompanies = document.querySelectorAll('.form-label__list--company');
formListCompanies.forEach(companyWrapper => {
    const companyHandler = companyWrapper.querySelector('.add-company-js');
    companyHandler.addEventListener('click', handleAddCompany);

    const periodHandlers = companyWrapper.querySelectorAll('.add-period-js');
    periodHandlers.forEach(periodHandler => {
        periodHandler.addEventListener('click', handleAddPeriod);
    });
});

/*
const form = document.querySelector('.form');
form && form.addEventListener('submit', function (e) {
    e.preventDefault();
    let data = new FormData(this);
    data = Object.fromEntries(data);
    fetch(form.getAttribute('action'), {
        method: "post",
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(data => {
            console.log(data); // Делайте что-то с ответом
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
});
*/

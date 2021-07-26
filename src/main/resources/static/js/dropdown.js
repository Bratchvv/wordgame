function showMenu(id)
{
  hideMenu();
  document.getElementById(id).classList.toggle("show");
}

function hideMenu()
{
  const dropdowns = document.getElementsByClassName("dropdown-content");
  let i;
  for(i = 0; i < dropdowns.length; i++)
  {
    const openDropdown = dropdowns[i];
    if(openDropdown.classList.contains('show'))
    {
      openDropdown.classList.remove('show');
    }
  }
}

window.onclick = function(event)
{
  if(!event.target.matches('.table-menu-item'))
  {
    hideMenu();
  }
};
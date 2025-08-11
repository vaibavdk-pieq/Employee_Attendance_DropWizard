package DAO.ENUM

enum class Departments {
    HR,
    IT,
    MARKETING,
    FINANCE,
    SALES,
    // add others as needed
}

enum class Roles(val role: String) {
    TEAM_LEAD("team_lead"),
    DEVELOPER("developer"),
    MANAGER("manager"),
    // add others as needed
}

package com.mmoreno.mycustomcard.model

object DataRepository {

    val listOfCustomCards = arrayListOf<CustomCard>()

    init {
        listOfCustomCards.add(
            CustomCard(
                "C++", """
            This is the first programming language I knew, I used it when I attended to my first
            programming course, I have only created educational projects not for enterprise.
            I don't really like it.
        """.trimIndent(), "ic_c_plus_plus"
            )
        )
        listOfCustomCards.add(
            CustomCard(
                "Java", """
            Java is the second programming language I knew, and this is my favorite programming language
            I have created educational and enterprise projects for about 2 and a half years, I have also programmed 
            using Java EE.
        """.trimIndent(), "ic_java"
            )
        )
        listOfCustomCards.add(
            CustomCard(
                "Kotlin", """
            My new baby :D, this is my new favorite language I'm learning it at the moment I'm creating
            new applications with the help of the awesome RayWenderlich mentors.
        """.trimIndent(), "ic_kotlin"
            )
        )
        listOfCustomCards.add(
            CustomCard(
                "Python", """
            With Python I worked about three or four months and just modified a few functionalities in an ERP software,
            not so much to say, I prefer static type programming languages. 
        """.trimIndent(), "ic_python"
            )
        )
        listOfCustomCards.add(
            CustomCard(
                "PL/SQL", """
            PL SQL for Oracle is one of the programming languages I have used the most, I used it to create
            stored procedures, triggers, functions inside the database. I think I have an intermediate level of knowledge with 
            this programming language.
        """.trimIndent(), "ic_pl_sql"
            )
        )
        listOfCustomCards.add(
            CustomCard(
                "Php", """
            With this programmming language I have only created one project at high school :(.
        """.trimIndent(), "ic_php"
            )
        )
        listOfCustomCards.add(
            CustomCard(
                "JavaScript", """
            With JavaScript I programmed about six months, nothing interesting just a pair of functions. I really prefer
            static type programming languages, the dynamic ones make me crazy. 
        """.trimIndent(), "ic_javascript"
            )
        )
    }
}
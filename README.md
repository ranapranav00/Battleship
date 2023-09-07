# Battleship
A modified version of traditional battleship, can run player vs player, player vs bot, or bot vs bot.


## Description
Essentially a terminal Battleship game between any combination of players or AI. AI utilizes a parity
based algorithm to increase it's odds of hitting a ship tile. Project utilizes MVC guidelines to separate 
program responsibilities. Can play locally with another person/bot, or play on a server as seen in instructions below.


## Getting Started

First clone the repository and run it in an IDE of your choice.

- git clone "https://github.com/ranapranav00/Battleship.git"

1. Playing locally
    - Simply modify the player1/player2 variable in the Driver.java file to be ManualPlayer or AiPlayer depending on your preference.
    - Run program with 0 arguments

2. Playing on externally hosted server
    - Enter arguments as labeled in comments in Driver.java file.


This template includes several additional tools:
1. Gradle Build Automation
1. JaCoCo for Test Coverage
1. CheckStyle for Code Style Checks (Using the custom [cs3500 check file](./config/checkstyle/cs3500-checkstyle.xml)) 

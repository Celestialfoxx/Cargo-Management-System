#3Dogs Cargo Management System
##Introduction

##How to use
1. Run `/3Dogs/src/layout/Login.java` in the configtion below.
2. Set VM arguments as  `--add-modules javafx.controls,javafx.fxml`.
3. Unselect option `Use the -XstartOnFirstThread argument when launching with SWT`.
4. Unselect option `Use the -XX:+ShowCodeDetailsInExceptionMessages argument whent launching`.
5. Run.

##Account for Login
**Admin** account is for System Administrator. It can modify whole system and manage accounts.
**Alice** and **Bob** are manager, they can edit the detail of the table in the system such as Cargo, Repository.
**Cold** is a general staff, he has no privilege to edit any table but only can browse the data.

|     Username     |     Password     |
| ------------ | ------------ |
| admin | admin123 |
| alice.johnson |  123 |
| bob.brown | 456 |
| cold.octopus | 789 |

The operations and functions of each interface after login have been shown in the Demo video, please follow the steps in the video to try them.

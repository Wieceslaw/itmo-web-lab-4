package ru.ifmo.se.lab4.util.exceptions

class UserNotFoundException: RuntimeException
{
    constructor(username: String) : super("User \"$username\" not found")
    constructor(id: Long) : super("User with id $id not found")
}

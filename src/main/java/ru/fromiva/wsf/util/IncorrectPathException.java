package ru.fromiva.wsf.util;

import lombok.experimental.StandardException;

/** Checked exception thrown when a path provided by a user is not correct. */
@StandardException
public class IncorrectPathException extends Exception { }

package ru.fromiva.wsf.folder;

import lombok.experimental.StandardException;

/** Checked exception thrown when a path provided by a user is not correct. */
@StandardException
public class IncorrectPathException extends Exception { }

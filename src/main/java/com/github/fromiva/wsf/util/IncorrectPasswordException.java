package com.github.fromiva.wsf.util;

import lombok.experimental.StandardException;

/** Checked exception thrown when a password provided by a user is not correct. */
@StandardException
public class IncorrectPasswordException extends Exception { }

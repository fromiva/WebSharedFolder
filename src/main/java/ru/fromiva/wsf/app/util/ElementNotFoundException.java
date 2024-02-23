package ru.fromiva.wsf.app.util;

import lombok.experimental.StandardException;

/** Checked exception thrown when repository or service can't retrieve a required element. */
@StandardException
public class ElementNotFoundException extends Exception { }

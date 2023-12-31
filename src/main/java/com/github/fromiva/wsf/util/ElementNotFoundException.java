package com.github.fromiva.wsf.util;

import lombok.experimental.StandardException;

/** Checked exception thrown when repository or service can't retrieve a request element. */
@StandardException
public class ElementNotFoundException extends Exception { }

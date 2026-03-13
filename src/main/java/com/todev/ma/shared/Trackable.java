package com.todev.ma.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Trackable {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
}

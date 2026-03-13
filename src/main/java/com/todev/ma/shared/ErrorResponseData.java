package com.todev.ma.shared;

import java.util.Set;

public record ErrorResponseData(String message, Set<String> details) {
}

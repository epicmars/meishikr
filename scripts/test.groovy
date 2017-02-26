#! /usr/bin/env groovy
import java.util.regex.Matcher
import java.util.regex.Pattern

def word = "wordCaseId"

def camelToUnder(str) {
    Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
    StringBuilder builder = new StringBuilder(str);
    for (int i = 0; matcher.find(); i++) {
        builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
    }
    if (builder.charAt(0) == '_') {
        builder.deleteCharAt(0);
    }
    return builder.toString();
}

String[] a = new String[0]
String[] b = ["a", "b"]

print a
print b

print camelToUnder(word)

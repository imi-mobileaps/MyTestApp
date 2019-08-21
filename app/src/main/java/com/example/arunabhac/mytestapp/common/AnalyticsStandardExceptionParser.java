package com.example.arunabhac.mytestapp.common;

import android.content.Context;

import com.google.android.gms.analytics.StandardExceptionParser;

import java.util.Collection;

public class AnalyticsStandardExceptionParser extends StandardExceptionParser {

    public AnalyticsStandardExceptionParser(Context context,
                                            Collection<String> additionalPackages) {
        super(context, additionalPackages);
    }

    @Override
    public String getDescription(String p_thread, Throwable p_throwable) {
        StringBuilder builder = new StringBuilder();

        builder.append(p_throwable.getMessage() + " ----- " + p_throwable.getLocalizedMessage() + "");
        builder.append("\n");
        builder.append("###");

        StackTraceElement[] stes = p_throwable.getStackTrace();
        for (int i = 0; i < stes.length; i++) {
            StackTraceElement ste = stes[i];
            builder.append(ste.getClassName() + "   " + ste.getFileName() + " "
                    + ste.getLineNumber() + "   " + ste.getMethodName());
            builder.append("\n");
            builder.append("###");
            builder.append("\n");
        }

        return "Thread: " + p_thread + ", Exception: " + builder.toString();
    }

}
package org.mega65.m65dbgui.domain;

import java.util.Map;

public record Register(String type, String hex, String db7, String db6, String db5, String db4,String db3,String db2,String db1,String db0, Map<String,String> description) {
}

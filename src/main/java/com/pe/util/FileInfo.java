/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pe.util;

/**
 *
 * @author dbermudez
 */
public class FileInfo {

    private String fileName;
    private long fileSize;

    public FileInfo() {
    }

    public FileInfo(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}

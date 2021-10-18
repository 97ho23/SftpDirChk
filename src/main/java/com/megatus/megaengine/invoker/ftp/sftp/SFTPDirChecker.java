package com.megatus.megaengine.invoker.ftp.sftp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTPDirChecker {

    private static void connect() throws JSchException {
        
        String hostIP = "192.168.10.207";
        int hostPort = 22;
        String userID = "gpadm";
        String userPW = "gpadm";

        Session session = null;

        JSch jsch = new JSch();

        session = jsch.getSession(userID, hostIP, hostPort);
        session.setPassword(userPW);

        java.util.Properties config = new java.util.Properties();

        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        try {
            session.connect();

            Channel channel = session.openChannel("sftp");
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.connect();

            StringBuffer sb = new StringBuffer();
            String ftpDir = "/home/gpadm/files/sftp/send";

            // 디렉토리 접근
            channelSftp.cd(ftpDir);

            // 디렉토리 목록 조회
            Vector<ChannelSftp.LsEntry> flist = channelSftp.ls(ftpDir);

            for (ChannelSftp.LsEntry entry : flist) {
                if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0) {
                    System.out.println("파일명 : " + entry.getFilename());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JSchException {
        connect();
    }
}

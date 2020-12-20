/**
 *      Copyright 2020 Daniel Sanchez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package thegang.bot.imagedraw;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.dv8tion.jda.api.entities.User;

public class CreateBirthdayImage {

    public static File start(User user, String name) {

        System.out.println("starting");
        // String id = event.getUser().getId();
        JFrame f = new JFrame();
        Component p = new DrawImage(user, name);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(1280, 720 + 28));
        f.add(p);
        f.setVisible(true);

        BufferedImage bi = new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        System.out.println(p.getWidth() + "  " + p.getHeight() + " " + f.getWidth() + " " + f.getHeight());
        p.print(graphics);
        graphics.dispose();
        f.dispose();

        String filename = name + "-" + LocalDate.now().getYear();
        File file = new File("./media/bday/" + filename + ".png");
        try {
            ImageIO.write(bi, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");

        File send = new File("./media/bday/" + filename + ".png");
        if (send.exists()) {
            return send;

        } else {
            System.out.println("THIS FILE DOES NOT EXIST");
            return null;
        }

    }

}

class DrawImage extends JPanel {

    private static final long serialVersionUID = 1L;
    BufferedImage img = null;
    BufferedImage pfp = null;
    User user;
    String name;

    public DrawImage(User user, String name) {
        this.user = user;
        this.name = name;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            img = ImageIO.read(new File("./media/template.png"));
            // pfp = ImageIO.read(new File("./pfp.png"));

        } catch (IOException e) {
        }
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

        try {
            if (user != null) {
                URL url = null;

                if (user.getAvatarUrl() != null) {
                    url = new URL(user.getAvatarUrl());
                } else {
                    url = new URL(user.getDefaultAvatarUrl());
                }

                URLConnection urlConn = url.openConnection();
                urlConn.addRequestProperty("User-Agent", "Chrome");

                String contentType = urlConn.getContentType();

                System.out.println("contentType:" + contentType);

                InputStream is = urlConn.getInputStream();
                pfp = ImageIO.read(is);

            } else {
                pfp = ImageIO.read(new File("./media/fallbackpfp.jpg"));
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int imgWidth = 320;
        int imgHeight = 320;
        int imgposx = img.getWidth() / 2 - imgWidth / 2;
        int imgposy = img.getHeight() / 2 - imgHeight / 2 + 110;

        g.drawImage(pfp, imgposx, imgposy, imgWidth, imgHeight, null);

        g.setColor(new Color(0, 156, 255));
        Font font = new Font("Heal The World", 1, 60);
        // String text = event.getUser().getName() + "#" +
        // event.getUser().getDiscriminator();
        String text = name;
        g.setFont(font);
        g.drawString(text, 65, 688);

    }

}

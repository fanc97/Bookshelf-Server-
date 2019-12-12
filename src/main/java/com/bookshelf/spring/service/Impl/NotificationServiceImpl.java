package com.bookshelf.spring.service.Impl;

import com.bookshelf.spring.entity.Book;
import com.bookshelf.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNotification(User user) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("bookshelfns@gmail.com");
        mail.setSubject("Dobrodosli , u najbolju knjizaru u Novom Sadu");
        mail.setText("Dragi  "+ user.getUsername()+", knjizara Bookshelf osnovana je 1977, od tada cuvamo tradiciju , kao najveca i najstarija u gradu" +
                "nalazimo se u ulici Branka Bajica 56,Detelinara , Novi Sad, cekamo vas!");

        javaMailSender.send(mail);
    }

    public void orderBook(User user, Book book) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("bookshelfns@gmail.com");
        mail.setSubject("Uzivajte u knjizi");
        mail.setText("Postovani"+user.getUsername()+", narucili ste "+book.getTitle()+" autora "+book.getAuthor()+
                " ,koju mozete podici svaki radni dan od 08h do 16h na adresi Branka Bajica 56,Novi Sad");

        javaMailSender.send(mail);
    }
}

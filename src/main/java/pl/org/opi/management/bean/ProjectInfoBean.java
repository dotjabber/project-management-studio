package pl.org.opi.management.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
@Scope("view")
public class ProjectInfoBean {

    @Value("#{pom.build}")
    private String build;

    @Value("#{pom.version}")
    private String version;

    @Value("#{pom.name}")
    private String name;

    public String getBuild() throws java.text.ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

        Date date = simpleDateFormat.parse(build);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));

        return simpleDateFormat.format(date);
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

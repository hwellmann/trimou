package org.trimou.handlebars.i18n;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.trimou.AbstractEngineTest;
import org.trimou.engine.MustacheEngineBuilder;
import org.trimou.engine.config.Configuration;
import org.trimou.engine.config.ConfigurationKey;
import org.trimou.engine.locale.LocaleSupport;

import com.google.common.collect.ImmutableMap;

/**
 * @author Martin Kouba
 */
public class DateTimeFormatHelperTest extends AbstractEngineTest {

    @Override
    @Before
    public void buildEngine() {
        engine = MustacheEngineBuilder.newBuilder()
                .registerHelper("formatTime", new DateTimeFormatHelper())
                .setLocaleSupport(new LocaleSupport() {

                    @Override
                    public Locale getCurrentLocale() {
                        return Locale.ENGLISH;
                    }

                    @Override
                    public void init(Configuration configuration) {
                    }

                    @Override
                    public Set<ConfigurationKey> getConfigurationKeys() {
                        return Collections.emptySet();
                    }
                }).build();
    }

    @Test
    public void testInterpolation() {

        Calendar day = Calendar.getInstance();
        day.set(Calendar.YEAR, 2013);
        day.set(Calendar.MONTH, 0);
        day.set(Calendar.DAY_OF_MONTH, 1);
        day.set(Calendar.HOUR_OF_DAY, 13);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);

        long milis = day.getTimeInMillis();

        String expectedShort = "1/1/13 1:00 PM";
        String expectedMedium = "Jan 1, 2013 1:00:00 PM";
        String expectedCustom = "01-01-2013 13:00";

        Map<String, Object> data = ImmutableMap.<String, Object> of("calendar",
                day, "date", day.getTime(), "milis", milis);

        assertEquals(
                StringUtils.repeat(expectedMedium, "|", 3),
                engine.compileMustache(
                        "date_time_helper_medium",
                        "{{formatTime calendar style=\"medium\"}}|{{formatTime date style=\"medium\"}}|{{formatTime milis style=\"medium\"}}")
                        .render(data));
        assertEquals(
                StringUtils.repeat(expectedShort, "|", 3),
                engine.compileMustache(
                        "date_time_helper_short",
                        "{{formatTime calendar style=\"short\"}}|{{formatTime date style=\"short\"}}|{{formatTime milis style=\"short\"}}")
                        .render(data));
        assertEquals(
                StringUtils.repeat(expectedCustom, "|", 3),
                engine.compileMustache(
                        "date_time_helper_custom",
                        "{{formatTime calendar pattern=\"DD-MM-yyyy HH:mm\"}}|{{formatTime date pattern=\"DD-MM-yyyy HH:mm\"}}|{{formatTime milis pattern=\"DD-MM-yyyy HH:mm\"}}")
                        .render(data));
    }

}
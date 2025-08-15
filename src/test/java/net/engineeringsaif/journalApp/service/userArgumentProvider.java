package net.engineeringsaif.journalApp.service;

import net.engineeringsaif.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class userArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("aada").password("shyam").build()),
                Arguments.of(User.builder().userName("sdfsa").password("suraj").build())
        );
    }
}

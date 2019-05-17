package com.example.demo.book;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class BookPublishedEvent {
    private final UUID id;
    private final Instant published;

    public BookPublishedEvent(UUID id, Instant published) {
        this.id = id;
        this.published = published;
    }

    public UUID getId() {
        return id;
    }

    public Instant getPublished() {
        return published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookPublishedEvent)) {
            return false;
        }
        BookPublishedEvent that = (BookPublishedEvent) o;
        return Objects.equals(getId(), that.getId()) &&
            Objects.equals(getPublished(), that.getPublished());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPublished());
    }
}

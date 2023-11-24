package dev.drugowick.lists.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "lists")
public class MyList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 144)
    private String title;

    @Column(length = 400)
    private String description;
    private boolean active = true;

    @OneToMany(mappedBy = "list",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<MyListItem> items = new ArrayList<>();

    @CreatedDate
    private Long createdDate;

    private String username;

    public void addItem(MyListItem item) {
        this.items.add(item);
    }

    public List<MyListItem> getItems() {
        var items = this.items.stream()
                .filter(MyListItem::isActive)
                .toList();
        return items;
    }

    public List<MyListItem> getArchivedItems() {
        var items = this.items.stream()
                .filter(Predicate.not(MyListItem::isActive))
                .toList();
        return items;
    }

    public void deactivateItemByUUID(UUID itemUUID) {
        items.stream()
                .filter(item -> item.getId().equals(itemUUID))
                .findFirst()
                .ifPresent(MyListItem::deactivate);
    }

    public void activateItemByUUID(UUID itemUUID) {
        items.stream()
                .filter(item -> item.getId().equals(itemUUID))
                .findFirst()
                .ifPresent(MyListItem::activate);
    }
}

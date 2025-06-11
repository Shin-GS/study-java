## 이모지 관련 API

- 유니코드 기반의 이모지 감지 지원
- 다양한 이모지 속성 구분 가능 (프레젠테이션/모디파이어 등)

```java
public static boolean isEmoji(int codePoint);

public static boolean isEmojiPresentation(int codePoint);

public static boolean isEmojiModifier(int codePoint);

public static boolean isEmojiModifierBase(int codePoint);

public static boolean isEmojiComponent(int codePoint);

public static boolean isExtendedPictographic(int codePoint);
```

```java
void example() {
    System.out.println(Character.isEmoji(128512)); // true 😀
    System.out.println(Character.isEmoji(65));     // false ('A')
}
```

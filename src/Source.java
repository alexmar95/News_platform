import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Source {
	Pinterest,
	Reddit,
	Wikipedia,
	Antena3,
	RomaniaTV,
	RussiaToday,
	OTV;
	
  private static final List<Source> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random(System.currentTimeMillis());

  public static Source randomSource()  {
    return VALUES.get(RANDOM.nextInt(SIZE));
  }
}

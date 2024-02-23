package au.com.mag.booking.striker_profiles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.mag.booking.striker_profiles.model.User;

/**
 * This interface is the cool cat of repositories, dig? Like in Airplane! when everyone's trying to act all normal
 * while chaos reigns, this here's keeping it cool with user data. It's where you go when you wanna know who's who
 * in the user zoo. Whether you're looking for a buddy by their email or checking if someone's ARN is in the game,
 * this repository got your back. It's the place for finding folks and making sure no one's stepping on toes with
 * duplicate emails or ARNs. So, if you need to find a user or check if they're already groovin' in your system,
 * just ask this slick operator.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email, like seeking out the coolest cat at the party by their shiny disco ball.
     * If they're groovin' in the system, you'll know. If not, it's like they're dancing in another dimension.
     *
     * @param email The digital calling card of the user you're trying to spot in this digital dance floor.
     * @return An optional user, might be shaking it right here, or might be a wallflower in another database.
     */
    Optional<User> findByEmail(String email);

    /**
     * Looks up a user by their ARN, which is kinda like their secret DJ name. If they've got one, you'll find
     * them laying down tracks. If not, maybe they're out there in the ether, waiting for their beat to drop.
     *
     * @param arn The Access Record Number, a VIP pass to the user's data.
     * @return An optional user, either dropping beats in your system or ghosting the scene.
     */
    Optional<User> findByArn(int arn);

    /**
     * Checks if a user's email is already lighting up the disco ball in your system, making sure you don't double
     * book the DJ. It's like making sure there ain't two cats wearing the same fly outfit at your party.
     *
     * @param email The user's email, their digital threads.
     * @return true if this email is already getting down in your system, false if it's fresh to the scene.
     */
    boolean existsByEmail(String email);

    /**
     * Peeks to see if a user's ARN is already on the VIP list, making sure your club ain't got duplicate royalty.
     * It's checking if you've already got that special someone's secret handshake on file.
     *
     * @param arn The Access Record Number, the secret handshake.
     * @return true if the ARN is already bumping in your system, false if it's a new groove.
     */
    boolean existsByArn(int arn);   
}
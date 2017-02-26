package developer.essiorh.instaphotomaker.domain.common;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by eSSiorh
 * on 26.02.17
 */

public abstract class Interactor<Result> {
    private CompositeSubscription subscription;

    public Interactor() {
        subscription = new CompositeSubscription();
    }

    protected <R> void addSubscription(Subscriber<R> subscriber) {
        subscription.add(subscriber);
    }

    public void unsubscribe() {
        subscription.unsubscribe();
    }

}

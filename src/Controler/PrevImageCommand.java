package Controler;

import Presenter.ImagePresenter;

public class PrevImageCommand implements Command{
    private final ImagePresenter imagePresenter;

    public PrevImageCommand(ImagePresenter imagePresenter) {
        this.imagePresenter = imagePresenter;
    }

    @Override
    public void execute() {
        imagePresenter.show(imagePresenter.image().prev());
    }
}

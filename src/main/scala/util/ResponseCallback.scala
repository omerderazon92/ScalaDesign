package util

trait ResponseCallback {
  def onSuccess()

  def onFailure()
}

package http.responses

case class ConsulKV(LockIndex: Int, Key: String, Flags: Int, Value: String, CreateIndex: Int, ModifyIndex: Int)

package com.eHanlin.hLog2.dispatch

abstract class MessageDispatch {
  /**
   * 當傳入的參數不會被發送時，丟出 RejectedExecutionException
   */
  def message(info : String)
}